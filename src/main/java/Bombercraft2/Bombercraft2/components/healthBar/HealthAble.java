package Bombercraft2.Bombercraft2.components.healthBar;

import Bombercraft2.Bombercraft2.core.Visible;

public interface HealthAble extends Visible {
    int getMaxHealth();

    int getActHealth();
}