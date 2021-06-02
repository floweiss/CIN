package at.technikumwien.personwebapp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PersonRepository personRepository;

    @RequestMapping("/")
    public String index(@RequestParam(defaultValue = "false") boolean all, Model model) {
        //System.out.println("---------- index ----------");

        var persons = (all ? personRepository.findAll() : personRepository.findAllByActiveTrue());
        if (all) {
            model.addAttribute("all", true);
        }

        model.addAttribute("persons", persons);
        return "index";
    }
}
