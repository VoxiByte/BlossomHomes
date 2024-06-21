package dev.codedsakura.blossom.homes;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.query.QueryMode;
import net.luckperms.api.query.QueryOptions;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Comparator;
import java.util.Optional;

public class LuckpermsHook {

    public static int getMaxHomes(ServerPlayerEntity player) {
        LuckPerms luckPerms = LuckPermsProvider.get();

        User user = luckPerms.getUserManager().getUser(player.getUuid());
        assert user != null;

        Optional<Integer> maxHomes = user.resolveInheritedNodes(QueryOptions.builder(QueryMode.NON_CONTEXTUAL).build()).stream()
                .filter(node -> node instanceof PermissionNode)
                .filter(node -> ((PermissionNode) node).getPermission().startsWith("blossom.home.limit."))
                .map(node -> Integer.parseInt(((PermissionNode) node).getPermission().substring(19)))
                .max(Comparator.comparingInt(i -> i));

        return maxHomes.orElse(-1);
    }

}
