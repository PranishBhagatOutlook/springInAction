// tag::head[]
package se.macbookstore.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import se.macbookstore.Ingredient;
import se.macbookstore.Ingredient.Type;
import se.macbookstore.Macbook;


@Slf4j
@Controller
@RequestMapping("/design")
public class DesignMacbookController {

//end::head[]

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("13SC", "13.6 screen", Type.SCREEN),
                new Ingredient("15SC", "15.6 Screen", Type.SCREEN),
                new Ingredient("MIKD", "Mini Keyboard", Type.KEYBOARD),
                new Ingredient("FLKD", "Full keyboard", Type.KEYBOARD),
                new Ingredient("35WC", "35W Charger", Type.CHARGER),
                new Ingredient("65WC", "65W charger", Type.CHARGER),
                new Ingredient("WRDM", "Wired Moue", Type.MOUSE),
                new Ingredient("WRLM", "Wireless Mouse", Type.MOUSE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute("design", new Macbook());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Macbook design, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }
    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
