package si.f5.mirageserver.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.GodMode;
import jdk.jfr.Name;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Expression GodMode")
@Description({"Returns(set, etc...) godmode of player using WorldGuard",
        "WorldGuardを使用してプレイヤーをgodモードにしたり解除したりできます。"})
@Examples({"on join:",
        "\tif godmode of player is false:",
        "\t\tset godmode of player to true",
        "\t\tsend \"Godmode enabled!\""})
@Since("1.0")
@RequiredPlugins("WorldGuard")
public class ExprGodMode extends SimplePropertyExpression<Player, Boolean> {

    static {

        register(ExprGodMode.class, Boolean.class, "godmode", "player");
    }

    private final WorldGuardPlugin worldGuard = WorldGuardPlugin.inst();
    private final ConfigurationManager manager = worldGuard.getGlobalStateManager();

    @Override
    public Class<? extends Boolean> getReturnType() {

        return Boolean.class;
    }

    @Override
    @Nullable
    public Boolean convert(Player player) {

        return manager.hasGodMode(player);
    }

    @Override
    protected String getPropertyName() {

        return "godmode";
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {

        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET)
            return CollectionUtils.array(Boolean.class);

        return null;
    }

    @Override
    public void change(Event event, Object[] delta, Changer.ChangeMode mode){

        Player player = getExpr().getSingle(event);
        if (player == null)
            return;

        Session session = worldGuard.getSessionManager().get(player);
        Boolean changeTo = false;
        if (delta != null)
            changeTo = (Boolean) delta[0];

        switch (mode) {

            case SET:
            case DELETE:
            case RESET:
                if (GodMode.set(player, session, changeTo)) {

                    if (changeTo)
                        player.setFireTicks(0);
                }
                break;
        }
    }
}