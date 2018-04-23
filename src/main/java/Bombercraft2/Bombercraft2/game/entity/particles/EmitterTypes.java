package Bombercraft2.Bombercraft2.game.entity.particles;

public enum EmitterTypes {
    PARTICLE_EMITTER_TEST("emitterTest"),
    PARTICLE_EXPLOSION_TEST("explosionTest"),
    PARTICLE_EXPLOSION_BOW_HIT("explosionBowHit"),
    PARTICLE_EXPLOSION_DEFAULT_HIT("explosionDefaultHit"),
    PARTICLE_EMITTER_GREEN_MAGIC("particleEmitterGreenMagic"),
    PARTICLE_EXPLOSION_BLUE_SPARK("particleExplosionBlueSpark");
    private final String name;

    EmitterTypes(String name) {this.name = name;}

    String getName() {return name;}
}
