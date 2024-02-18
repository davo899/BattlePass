package com.selfdot.battlepass.quest;

public enum PokemonRegion {

    KANTO,
    JOHTO,
    HOENN,
    SINNOH,
    UNOVA,
    KALOS,
    ALOLA,
    GALAR,
    PALDEA;

    public static PokemonRegion fromString(String string) {
        return switch (string.toUpperCase()) {
            case "KANTO" -> KANTO;
            case "JOHTO" -> JOHTO;
            case "HOENN" -> HOENN;
            case "SINNOH" -> SINNOH;
            case "UNOVA" -> UNOVA;
            case "KALOS" -> KALOS;
            case "ALOLA" -> ALOLA;
            case "GALAR" -> GALAR;
            case "PALDEA" -> PALDEA;
            default -> throw new IllegalStateException("Unexpected value: " + string.toUpperCase());
        };
    }

    public static String toString(PokemonRegion pokemonRegion) {
        return switch (pokemonRegion) {
            case KANTO -> "KANTO";
            case JOHTO -> "JOHTO";
            case HOENN -> "HOENN";
            case SINNOH -> "SINNOH";
            case UNOVA -> "UNOVA";
            case KALOS -> "KALOS";
            case ALOLA -> "ALOLA";
            case GALAR -> "GALAR";
            case PALDEA -> "PALDEA";
        };
    }

    public static PokemonRegion fromDexNumber(int dexNumber) {
        if      (dexNumber <= 151) return KANTO;
        else if (dexNumber <= 251) return JOHTO;
        else if (dexNumber <= 386) return HOENN;
        else if (dexNumber <= 493) return SINNOH;
        else if (dexNumber <= 649) return UNOVA;
        else if (dexNumber <= 721) return KALOS;
        else if (dexNumber <= 809) return ALOLA;
        else if (dexNumber <= 905) return GALAR;
        else                       return PALDEA;
    }

}
