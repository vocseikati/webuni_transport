package hu.katka.transport.services;

import hu.katka.transport.entities.Section;
import hu.katka.transport.repositories.SectionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

  @Autowired
  SectionRepository sectionRepository;

  public List<Section> getAllSections(){
    return sectionRepository.findAll();
  }
}
