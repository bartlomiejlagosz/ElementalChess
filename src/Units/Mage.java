package Units;

import Elements.Element;

public class Mage extends Unit {
    public Mage(Element e)
    {
        this.setType(new Type(TypeName.MAGE,TypeName.ARCHER,TypeName.TEMPLAR,"★"));
        this.setLife();
        this.setSpeed(2);
        this.setSight(8);
        this.setElement(e);
    }
}
