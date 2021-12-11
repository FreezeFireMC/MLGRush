package de.chaos.mc.server.mlgrush.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Permissions {
    DEVELOPER("FreezeFire.developer");
    @Getter private String permission;
}
