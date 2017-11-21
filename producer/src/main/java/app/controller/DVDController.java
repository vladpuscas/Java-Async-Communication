package app.controller;


import app.service.QueueService;
import entity.DVD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import repository.DVDRepository;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Vlad on 18-Nov-17.
 */
@Controller
@RequestMapping(value = "/index")
public class DVDController {

    @Autowired
    private DVDRepository dvdRepository;

    @Autowired
    private QueueService queueService;

    @RequestMapping(method = RequestMethod.GET)
    public String showForm() {
        return "/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void processForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DVD dvd = new DVD();
        dvd.setPrice(Double.parseDouble(request.getParameter("price")));
        dvd.setTitle(request.getParameter("title"));
        dvd.setYear(Integer.parseInt(request.getParameter("year")));

        dvd = dvdRepository.save(dvd);

        try {
            queueService.publishDVD(dvd);
        } catch (IOException e) {
            response.getWriter().println("IO ERROR");
        } catch (TimeoutException e) {
            response.getWriter().println("Timeout");
        }

        response.getWriter().println("DVD created successfully. Customers are being notified!");

    }
}
