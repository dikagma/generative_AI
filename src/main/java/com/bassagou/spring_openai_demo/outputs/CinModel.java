package com.bassagou.spring_openai_demo.outputs;

public record CinModel(String nom,
                       String prenom,
                       String dateNaissance,
                       String arabicFirstName,
                       String arabicLastName,
                       String dateValidite,
                       String numero,
                       String lieuNaissance
                       ) {
}
