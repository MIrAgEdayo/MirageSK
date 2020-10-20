package si.f5.mirageserver.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import jdk.jfr.Name;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

@Name("Expression Subaccount")
@Description({"Returns online subaccounts of players",
        "プレイヤーのオンラインのサブアカウントを取得します。"})
@Examples({"on join:",
        "\tif size of subaccounts of player > 3:",
        "\t\tkick the player due to \"You have too many subaccounts!\""})
@Since("1.0")
public class ExprSubAccounts extends SimpleExpression<Player> {

    static {

        Skript.registerExpression(ExprSubAccounts.class, Player.class, ExpressionType.SIMPLE, "[the] subaccount[s] of %players%");
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

        return "[the] subaccount[s] of %players%";
    }

    @Override
    @Nullable
    protected Player[] get(Event event) {

        if (this.player == null)
            return null;

        Player[] targets = this.player.getAll(event);
        List<Player> subAccounts = new ArrayList<>();

        for (Player target : targets) {

            String address = getIp(target);

            for (Player player : Bukkit.getOnlinePlayers()) {

                if (player.equals(target))
                    continue;
                if (getIp(player).equals(address))
                    subAccounts.add(player);
            }
        }
        return subAccounts.toArray(new Player[0]);
    }

    private String getIp(Player player) {

        if (player == null)
            return null;

        return player.getAddress().getAddress().getHostAddress();
    }
}