package hu.katka.transport;

import hu.katka.transport.services.InitDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransportApplication implements CommandLineRunner {

  @Autowired
  InitDbService initDbService;

  public static void main(String[] args) {
    SpringApplication.run(TransportApplication.class, args);

  }

  @Override
  public void run(String... args) throws Exception {
//    initDbService.deleteDb();
//    initDbService.insertTestData();
  }
}
