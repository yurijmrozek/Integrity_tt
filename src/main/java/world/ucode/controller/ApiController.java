package world.ucode.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.ucode.model.Words;

import java.util.ArrayList;
import java.util.List;

// Без сервисов веселее))

@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping
    public Words checkWords(@RequestBody Words words) {
        List<String> result = new ArrayList<>();

        if (words.getWords() != null) {
            List<String> wordList = words.getWords();
            Character previousWordLastChar = null;

            for (String word : wordList) {
                if (word.isEmpty()) {
                    break;
                }

                char firstChar = word.charAt(0);
                char currentLastChar = word.charAt(word.length() - 1);

                if (previousWordLastChar != null && firstChar != previousWordLastChar) {
                    break;
                }

                previousWordLastChar = currentLastChar;
                result.add(word);
            }
        }
        words.setWords(result);
        return words;
    }

}
