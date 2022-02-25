package hu.katka.transport.controllers;

import hu.katka.transport.dtos.SectionDto;
import hu.katka.transport.mapper.SectionMapper;
import hu.katka.transport.services.SectionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

  @Autowired
  SectionService sectionService;

  @Autowired
  SectionMapper mapper;

  @GetMapping
  public List<SectionDto> getAllSections(){
    return mapper.sectionsToDtos(sectionService.getAllSections());
  }
}
