package TemplateMethodPattern;

public class TestTemplatePattern {
    public static void main(String[] args) {
        WildDuck wildDuck = new WildDuck();
        DomesticDuck domesticDuck = new DomesticDuck();
        wildDuck.showItself();
        domesticDuck.showItself();
    }
}
