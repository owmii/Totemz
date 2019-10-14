package xieao.totemz;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Config GENERAL;
    public static final ForgeConfigSpec CONFIG_SPEC;

    public final ForgeConfigSpec.IntValue pointsPerKill;
    public final ForgeConfigSpec.IntValue pointsPerBossKill;
    public final ForgeConfigSpec.IntValue maxPoint1;
    public final ForgeConfigSpec.IntValue maxPoint2;
    public final ForgeConfigSpec.IntValue maxPoint3;
    public final ForgeConfigSpec.IntValue maxPoint4;

    public Config(ForgeConfigSpec.Builder builder) {
        this.pointsPerKill = builder.comment("Points per Kill.").defineInRange("pointsPerKill", 1, 1, 50);
        this.pointsPerBossKill = builder.comment("Points per boss Kill.").defineInRange("pointsPerBossKill", 10, 1, 100);
        this.maxPoint1 = builder.comment("Points needed to level up from level 1 to 2.").defineInRange("maxPoint1", 400, 1, Integer.MAX_VALUE);
        this.maxPoint2 = builder.comment("Points needed to level up from level 2 to 3.").defineInRange("maxPoint2", 800, 1, Integer.MAX_VALUE);
        this.maxPoint3 = builder.comment("Points needed to level up from level 3 to 4.").defineInRange("maxPoint3", 1200, 1, Integer.MAX_VALUE);
        this.maxPoint4 = builder.comment("Points needed to level up from level 4 to 5.").defineInRange("maxPoint4", 2000, 1, Integer.MAX_VALUE);
    }

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        CONFIG_SPEC = specPair.getRight();
        GENERAL = specPair.getLeft();
    }
}
