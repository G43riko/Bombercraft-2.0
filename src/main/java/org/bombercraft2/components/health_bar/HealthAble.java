package org.bombercraft2.components.health_bar;

import org.bombercraft2.core.Visible;

public interface HealthAble extends Visible {
    int getMaxHealth();

    int getActHealth();
}
