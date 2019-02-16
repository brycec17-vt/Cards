package io;

import deck.Card;
import deck.Side;
import leitner.LeitnerEnum;
import leitner.LeitnerSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LeitnerSetReader {
    private LeitnerSet leitnerSet;
    private File file;
    private Scanner scanner;
    private List<String> fieldNames;
    private LeitnerEnum[] enums = LeitnerEnum.values();

    class ReadException extends RuntimeException {
        ReadException(String message) { super(message); }
    }

    public LeitnerSetReader(File file) {
        String filePath = file.getPath();
        if (!filePath.endsWith(".deck")) {
            throw new ReadException("Invalid file extension.");
        }
        this.leitnerSet = new LeitnerSet();
        this.file = file;
        try {
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        this.fieldNames = new ArrayList<>();
    }

    public void read() {
        if (scanner.hasNext()) {
            String titleLine = scanner.nextLine();
            if (titleLine.endsWith(":")) {
                leitnerSet.setName(titleLine.substring(0, titleLine.length() - 1));
            }
            else {
                throw new ReadException("Expect title string as first line.");
            }
            while (scanner.hasNext()) {
                scanCard();
            }
        }
        else {
            throw new ReadException("Expect title string as first line.");
        }
    }

    private void scanCard() {
        String line = scanner.nextLine();
        line = line.substring(1, line.length() - 1);
        Map<String, Side> sides = readSides(line);
        leitnerSet.getLeitnerBox(LeitnerEnum.NOTINTRODUCED).add(new Card(sides.get("front"), sides.get("back")));
    }

    private Map<String, Side> readSides(String string) {
        Map<String, Side> sides = new HashMap<>();
        int i = 0;
        for (String side : string.split(",\\s+")) {
            side = side.substring(1, side.length() - 1);
            String[] arr = side.split(":");
            if (arr.length != 2) {
                throw new ReadException("Malformed card.");
            }
            if ("front".equals(arr[0].toLowerCase()) || "back".equals(arr[0].toLowerCase())) {
                sides.put(arr[0], new Side(arr[0], arr[1]));
            }
            else {
                throw new ReadException("Malformed card.");
            }
        }
        return sides;
    }

    public LeitnerSet getLeitnerSet() {
        return leitnerSet;
    }
}
