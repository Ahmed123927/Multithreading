package com.example.Mediation.service;

import com.example.Mediation.entity.Calls;
import com.example.Mediation.repository.CallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Component
public class FileParse {

    @Autowired
    private CallsRepository callsRepository;

    public void parseFileAndSave(String filePath) throws IOException {
        List<Calls> callsList = new LinkedList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 3) {
                    String source = tokens[0];
                    String destination = tokens[1];
                    int duration = Integer.parseInt(tokens[2]);
                    Calls call = new Calls(source, destination, duration);
                    System.out.println("Parsed " + call);
                    callsList.add(call);
                }
            }

            if (!callsList.isEmpty()) {
                callsRepository.saveAll(callsList);
                System.out.println("*****************************************************************");
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        finally {
            Path originalPath = Paths.get(filePath);
            Path parsedPath = Paths.get(filePath + ".parsed");
            Files.move(originalPath, parsedPath);
            System.out.println("renamed to: " + parsedPath);
        }
    }
}
