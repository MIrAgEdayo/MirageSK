package si.f5.mirageserver.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprSubAccounts extends SimpleExpression<Player> {

    static {

        Skript.registerExpression(ExprSubAccounts.class, Player.class, ExpressionType.PROPERTY, "[the] subaccount[s] of %player%");
    }

    private Expression<Player> player;

    @Override
    public Class<? extends Player> getReturnType() {

        return Player.class;
    }

    @Override
    public boolean isSingle() {

        return false;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        player = (Expression<Player>)exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {

        return "[the] subaccount[s] of " + player.toString(event, debug);
    }

    @Override
    @Nullable
    protected Player[] get(Event event) {

        Player target = this.player.getSingle(event);
        String address = getIp(target);
        List<Player> subAccounts = new ArrayList<>();

        if (player == null)
            return null;

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (player.equals(target))
                continue;
            if (getIp(player).equals(address))
                subAccounts.add(player);
        }
        return subAccounts.toArray(new Player[0]);
    }

    private String getIp(Player player) {

        if (player == null)
            return null;

        return player.getAddress().getAddress().getHostAddress();
    }
}