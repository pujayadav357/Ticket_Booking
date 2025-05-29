package org.example.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {
    private List<Train> trainList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAIN_DB_PATH = "data/trains.json";

    public TrainService() throws IOException {
        loadTrainListFromFile();
    }

    private void loadTrainListFromFile() throws IOException {
        System.out.println("Loading train list from: " + TRAIN_DB_PATH);
        File file = new File(TRAIN_DB_PATH);
        try {
            if (file.exists()) {
                trainList = objectMapper.readValue(file, new TypeReference<List<Train>>() {});
                System.out.println("Loaded " + trainList.size() + " trains");
            } else {
                System.out.println("Train file not found at " + file.getAbsolutePath() + ". Initializing empty train list.");
                trainList = new ArrayList<>();
            }
        } catch (IOException ex) {
            System.err.println("Error loading train list: " + ex.getMessage());
            throw ex;
        }
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream().filter(train -> validTrain(train, source, destination)).collect(Collectors.toList());
    }

    public void addTrain(Train newTrain) {
        Optional<Train> existingTrain = trainList.stream()
                .filter(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainId()))
                .findFirst();
        if (existingTrain.isPresent()) {
            updateTrain(newTrain);
        } else {
            trainList.add(newTrain);
            saveTrainList();
        }
    }

    public void updateTrain(Train updatedTrain) {
        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId()))
                .findFirst();
        if (index.isPresent()) {
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainList();
        } else {
            addTrain(updatedTrain);
        }
    }

    private void saveTrainList() {
        try {
            File outputFile = new File(TRAIN_DB_PATH);
            outputFile.getParentFile().mkdirs();
            objectMapper.writeValue(outputFile, trainList);
        } catch (IOException ex) {
            System.err.println("Error saving train data: " + ex.getMessage());
        }
    }

    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();
        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());
        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }
}