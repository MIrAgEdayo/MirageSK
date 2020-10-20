package si.f5.mirageserver.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import jdk.jfr.Name;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.inventivetalent.glow.GlowAPI;
import si.f5.mirageserver.misc.Utils;

import javax.annotation.Nullable;
import java.util.Arrays;

@Name("Effect Glow")
@Description({"Entities can be made to glow or unglow with a specified color only for a specific player.",
        "エンティティを指定した色で特定のプレイヤーにだけ発光させたり、解除したりできます。"})
@Examples({"on join:",
        "\tforce player glowing with color \"red\" for all players",
        "\twait 3 second",
        "\tforce player unglow for all players"})
@Since("1.0")
@RequiredPlugins({"GlowAPI", "PacketListenerAPI"})
public class EffGlow extends Effect {

    static {

        Skript.registerEffect(EffGlow.class,
                "[(make|force)] %entities% glow[ing] with color %string% [(for|to) %-players%]",
                "[(make|force)] %entities% unglow[ing] (for|to) %players%");
    }

    private Expression<Entity> entity;
    private Expression<String> color;
    private Expression<Player> glowTo;
    private Boolean isGlow;

    @Override
    public String toString(@Nullable Event event, boolean debug) {

        return "glow player effect with expression player: " + entity.toString(event, debug);
    }

    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        this.entity = (Expression<Entity>) expressions[0];
        this.isGlow = matchedPattern == 0;
        if (this.isGlow) {

            this.color = (Expression<String>) expressions[1];
            this.glowTo = (Expression<Player>) expressions[2];
        } else
            this.glowTo = (Expression<Player>) expressions[1];

        return true;
    }

    @Override
    protected void execute(Event event) {

        Entity[] entities = this.entity.getAll(event);
        String color = null;
        if (this.isGlow)
            color = this.color.getSingle(event);
        Player[] effectTarget = this.glowTo.getAll(event);

        if (isGlow) {
            if (Utils.isEnumExist(GlowAPI.Color.class, (color + "").toUpperCase()))
                GlowAPI.setGlowing(Arrays.asList(entities), GlowAPI.Color.valueOf((color + "").toUpperCase()), Arrays.asList(effectTarget));
        } else
            GlowAPI.setGlowing(Arrays.asList(entities), null, Arrays.asList(effectTarget));
    }
}