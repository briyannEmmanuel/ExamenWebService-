package com.groupeisi.controller;

import com.groupeisi.DTO.DayInfoDTO;
import com.groupeisi.entity.DateHistorique;
import com.groupeisi.repository.DateHistoriqueRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CalendarController {

    @Autowired
    private DateHistoriqueRepository dateHistoriqueRepository;

    @PostMapping("/services/calendar/dayfinder")
    public DayInfoDTO findDayOfWeek(@RequestBody String date) {
        // Code pour trouver le jour de la semaine comme déjà implémenté
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DayOfWeek dayOfWeek = parsedDate.getDayOfWeek();
        String formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String dayOfWeekString = dayOfWeek.toString();

        // Enregistrement de la date dans l'historique
        DateHistorique dateHistorique = new DateHistorique();
        dateHistorique.setDateRecherchee(parsedDate);
        dateHistoriqueRepository.save(dateHistorique);

        // Création d'un objet DayInfoDTO avec les valeurs appropriées
        DayInfoDTO dayInfoDTO = new DayInfoDTO();
        dayInfoDTO.setDate(formattedDate);
        dayInfoDTO.setDayOfWeek(dayOfWeekString);

        return dayInfoDTO;
    }

    @GetMapping("/historique/all")
    public List<DateHistorique> getAllDates() {
        return dateHistoriqueRepository.findAll();
    }
}
