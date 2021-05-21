package com.blps.airlineservice.service;

import com.blps.airlineservice.model.*;
import com.blps.airlineservice.repository.BookRepository;
import com.blps.airlineservice.repository.LogRepository;
import com.blps.airlineservice.repository.ReportRepository;
import com.blps.airlineservice.repository.TaskRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final LogRepository logRepository;
    private final TaskRepository taskRepository;
    private final BookRepository bookRepository;
    private final ReportRepository reportRepository;
    private final KafkaTemplate<String, String> responceTemplate;

    public ReportService(LogRepository logRepository,
                         TaskRepository taskRepository,
                         BookRepository bookRepository,
                         KafkaTemplate<String, String> responceTemplate,
                         ReportRepository reportRepository) {
        this.logRepository = logRepository;
        this.taskRepository = taskRepository;
        this.bookRepository = bookRepository;
        this.responceTemplate = responceTemplate;
        this.reportRepository = reportRepository;
    }

    private List<Log> getLogsFiltered(Task task, Company company){
        List<Log> logsCompany = null;

        if (task.getEndStamp() != null && task.getStartStamp() != null){
            logsCompany = logRepository.findByCompanyNameAndCreateTimeAfterAndCreateTimeBefore(
                    company.getName(),
                    task.getStartStamp(),
                    task.getEndStamp());
        } else if(task.getEndStamp() != null){
            logsCompany = logRepository.findByCompanyNameAndCreateTimeBefore(
                    company.getName(),
                    task.getEndStamp()
            );
        } else if (task.getStartStamp() != null){
            logsCompany = logRepository.findByCompanyNameAndCreateTimeAfter(
                    company.getName(),
                    task.getStartStamp()
            );
        } else {
            logsCompany = logRepository.findByCompanyName(company.getName());
        }

        return logsCompany;
    }

    private boolean isFilterTicketByPlace(Task task, Flight flight){

        if(task.getPlaceFrom() != null && task.getPlaceTo() != null){
            return flight.getPlaceFrom().equals(task.getPlaceFrom()) && flight.getPlaceTo().equals(task.getPlaceTo());
        } else if (task.getPlaceFrom() != null){
            return flight.getPlaceFrom().equals(task.getPlaceFrom());
        } else if (task.getPlaceTo() != null){
            return flight.getPlaceTo().equals(task.getPlaceFrom());
        }
        return true;
    }

    private void completeTask(Task task){
        User user = task.getCreator();
        Company company = user.getCompany();
        List<Log> logsCompany = getLogsFiltered(task, company);

        List<ReportRow> reportRows = new LinkedList<>();
        for(Log log : logsCompany){
            Optional<Book> book = bookRepository.findById(log.getBookId());
            if(book.isPresent()){
                List<Ticket> tickets = book.get().getTickets();
                for(Ticket ticket : tickets){
                    Flight flight = ticket.getFlight();
                    if(isFilterTicketByPlace(task, flight)){
                        ReportRow reportRow = new ReportRow();
                        reportRow.setBoss_login(book.get().getBoss().getLogin());
                        reportRow.setCreator_login(user.getLogin());
                        reportRow.setAirline(flight.getAirline());
                        reportRow.setCreation_time(log.getCreateTime());
                        reportRow.setArrival_time(flight.getArrivalTime());
                        reportRow.setDeparture_time(flight.getDepartureTime());
                        reportRow.setStatus(book.get().getStatus());
                        reportRow.setPlace_from(flight.getPlaceFrom());
                        reportRow.setPlace_to(flight.getPlaceTo());
                        reportRow.setCost(flight.getCost());
                        reportRows.add(reportRow);
                    }
                }

            }
        }

        createDock(reportRows, task.getId());
        responceTemplate.send("aviasales.tasks.response", task.getId() +" completed");
        taskRepository.delete(task);
    }

    public void createDock(List<ReportRow> reportRows, long id){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(id+".pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            document.add(new Chunk("Report", font));
            for(ReportRow row : reportRows){
                document.add(new Paragraph("\n"));
                document.add(new Chunk(row.toString(), font));
            }
            document.close();
            Path path = Paths.get(id+".pdf");
            Report report = new Report();
            report.setId(id);
            report.setContent(Files.readAllBytes(path));
            reportRepository.saveAndFlush(report);
            Files.delete(path);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createStats(){
        List<Task> tasks = taskRepository.findAll();
        for(Task task : tasks){
            completeTask(task);
        }
    }
}
