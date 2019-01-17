package Utility;

import Game.Game;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Field {
    private Text text;
    private Rectangle fieldSquare;
    private Slot fieldSlot;
    private int fieldID;

     Field(int fieldID,Game game, CheckBox moveCheckBox, CheckBox attackCheckBox) {
        this.fieldSquare = null;
        this.fieldSlot = null;
        this.fieldID = fieldID;
        this.text = new Text(50,50,"");
        EventHandler<MouseEvent> eventMouseClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(fieldSlot!=null && fieldSlot.getPlayer().getPlayerNumber()==game.getPlayerTurn()) {
                    if (fieldID == game.getEnabledFieldID()) {
                        BoardSetter.createGlow(0,text);
                        game.setEnabledFieldID(-1);
                        BattlefieldManager.setBoardColor(Color.GREEN, game);
                    } else {
                        for (Field f : game.getFields()) {
                            BoardSetter.createGlow(0,f.getText());
                            f.getFieldSquare().setFill(Color.GREEN);
                        }
                        game.setEnabledFieldID(fieldID);
                        BoardSetter.createGlow(4,text);
                        if (moveCheckBox.isSelected()) {
                            colorFieldsInRange(game, fieldID, Color.DARKORANGE, fieldSlot.getUnitName().getSpeed());
                        } else {
                            if (attackCheckBox.isSelected()) {
                                colorFieldsInRange(game, fieldID, Color.YELLOW, fieldSlot.getUnitName().getSight());
                            }
                        }
                    }
                }
            }

        };
        this.text.addEventFilter(MouseEvent.MOUSE_CLICKED,eventMouseClick);
    }
    static void colorFieldsInRange(Game game, int fieldID,Color color,int range)
    {
        for (Field f : game.getFields()) {
            if (Field.checkAvailability(range, fieldID, f.getFieldID(), game)) {
                f.getFieldSquare().setFill(color);
            }
        }
    }

    public Text getText() {
        return text;
    }

    void setFieldSquare(Rectangle fieldSquare) {
        this.fieldSquare = fieldSquare;
    }

    void setFieldSlot(Slot fieldSlot) {
        this.fieldSlot = fieldSlot;
    }

    public Rectangle getFieldSquare() {
        return fieldSquare;
    }

    public Slot getFieldSlot() {
        return fieldSlot;
    }

    int getFieldID() {
        return fieldID;
    }
    static boolean checkAvailability(int steps,int actualFieldID,int targetFieldID, Game game)
    {
        if(steps>=0 && actualFieldID==targetFieldID)
        {
            return true;
        }
        else
        {
            if(steps==0)
            {
                return false;
            }
            else
            {
                boolean left= false;
                boolean right = false;
                boolean up = false;
                boolean down = false;
                if((actualFieldID+1)%12!=0)
                {
                    right=checkAvailability(steps-1,actualFieldID+1,targetFieldID,game);
                }
                if((actualFieldID-1)%12!=11 && actualFieldID!=0)
                {
                    left=checkAvailability(steps-1,actualFieldID-1,targetFieldID,game);
                }
                if((actualFieldID-12)>=0)
                {
                    up=checkAvailability(steps-1,actualFieldID-12,targetFieldID,game);
                }
                if((actualFieldID+12)<144)
                {
                    down=checkAvailability(steps-1,actualFieldID+12,targetFieldID,game);
                }
                return (up||down||left||right);
            }
        }
    }
}
