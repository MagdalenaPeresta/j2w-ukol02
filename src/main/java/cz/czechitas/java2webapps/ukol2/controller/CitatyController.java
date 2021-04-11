package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class CitatyController {
    private final Random random;
    public CitatyController(){
        random = new Random();
    }

    private static List<String> readAllLines(String resource)throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }

    }
    private int getRandomNumber(int upperbound){
        return random.nextInt(upperbound);
    }


    @GetMapping("/")
    public ModelAndView citaty() throws IOException {

        List<String> citaty = readAllLines("citaty.txt");
        ModelAndView result = new ModelAndView("citat");
        String randomCitat = citaty.get(getRandomNumber(citaty.size()));
        result.addObject("citat",randomCitat);

        List<String> obrazky = readAllLines("obrazky.txt");
        String randomObrazek = obrazky.get(getRandomNumber(obrazky.size())).trim();
        result.addObject("obrazek",randomObrazek);

        return result;
    }




}
