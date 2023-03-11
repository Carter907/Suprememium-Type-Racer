package org.example.supremium.model;

import org.example.supremium.AppStart;

import java.io.*;
import java.nio.Buffer;
import java.util.Random;

public class PromptManager {

    public PromptManager() {

    }

    public String getNewPrompt() {

        return getPrompt(((int)(Math.random()*(getPromptCount())))+1);
    }

    private int getPromptCount() {
        int promptCount = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(AppStart.class.getResourceAsStream("prompts.txt")))) {

            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#prompt_"))
                    promptCount++;
            }
            return promptCount;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return promptCount;
    }

    private String getPrompt(int promptId) {
        StringBuilder prompt = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(AppStart.class.getResourceAsStream("prompts.txt")))) {

            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#prompt_"+promptId)) {
                    line = line.substring(line.indexOf(promptId+"")+2);
                    do {
                        prompt.append(line);
                        line = reader.readLine();
                    } while (line != null && !line.isEmpty());
                    break;
                }

            }
            return prompt.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return prompt.toString().trim();
    }

}
