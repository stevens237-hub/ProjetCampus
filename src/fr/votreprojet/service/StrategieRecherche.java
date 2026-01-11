package fr.votreprojet.service;

import fr.votreprojet.modele.Annonce;
import java.util.List;
import java.util.Map;

public interface StrategieRecherche {
    List<Annonce> rechercher(Map<String, Object> criteres);
}