package com.pWeb.proiect.Controllers;


import com.pWeb.proiect.DataModel.Image;
import com.pWeb.proiect.Services.JmsPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/")
public class ImageController {

    @Autowired

    JmsPublisher jmsPublisher;

    @PostMapping()
    public void sendInfo(@RequestBody Image image) {
        System.out.println(image);
        jmsPublisher.publishImage(image);
    }


}
