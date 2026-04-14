package com.codisimus.plugins.phatloots.loot;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;
import org.bukkit.inventory.ItemStack;

/**
 * A LootBundle is a collection of items, commands, money, and experience that has been rolled for
 *
 * @author Codisimus
 */
public class LootBundle {
    public record RareItemDrop(ItemStack item, double effectiveChancePercent) {}

    private final List<ItemStack> itemList;
    private final List<RareItemDrop> rareItemDrops = new LinkedList<>();
    private final List<CommandLoot> commandList = new LinkedList<>();
    private final List<String> messageList = new LinkedList<>();
    private final Deque<Double> chanceMultipliers = new ArrayDeque<>();
    private double money = 0;
    private int exp = 0;

    /**
     * Creates a new LootBundle with the given list of ItemStacks
     *
     * @param itemList The preexisting list of items
     */
    public LootBundle(List<ItemStack> itemList) {
        this.itemList = itemList;
        chanceMultipliers.push(1.0D);
    }

    /**
     * Creates a new, empty LootBundle
     */
    public LootBundle() {
        this.itemList = new LinkedList<>();
        chanceMultipliers.push(1.0D);
    }

    /**
     * Returns the list of items in the bundle
     *
     * @return The List of ItemStacks in the bundle
     */
    public List<ItemStack> getItemList() {
        return itemList;
    }

    /**
     * Adds the given item to the list of looted items
     *
     * @param item The given ItemStack to add as loot
     */
    public void addItem(ItemStack item) {
        addItem(item, getCurrentChancePercent());
    }

    /**
     * Adds the given item to the list of looted items and tracks its effective chance
     *
     * @param item The given ItemStack to add as loot
     * @param effectiveChancePercent The effective chance in percent that produced the item
     */
    public void addItem(ItemStack item, double effectiveChancePercent) {
        itemList.add(item);
        rareItemDrops.add(new RareItemDrop(item, effectiveChancePercent));
    }

    /**
     * Returns all looted items with their effective chances
     *
     * @return The list of looted items and effective chance percentages
     */
    public List<RareItemDrop> getRareItemDrops() {
        return rareItemDrops;
    }

    /**
     * Pushes a chance multiplier to apply while rolling nested loot
     *
     * @param multiplier The multiplier to push
     */
    public void pushChanceMultiplier(double multiplier) {
        chanceMultipliers.push(chanceMultipliers.peek() * multiplier);
    }

    /**
     * Pops the last chance multiplier
     */
    public void popChanceMultiplier() {
        if (chanceMultipliers.size() > 1) {
            chanceMultipliers.pop();
        }
    }

    /**
     * Returns the current effective chance in percent
     *
     * @return The current effective chance percent
     */
    public double getCurrentChancePercent() {
        return chanceMultipliers.peek() * 100.0D;
    }

    /**
     * Returns the list of commands to be executed
     *
     * @return The list of CommandLoots
     */
    public List<CommandLoot> getCommandList() {
        return commandList;
    }

    /**
     * Adds the given command to the list of looted commands
     *
     * @param command The given CommandLoot to add as loot
     */
    public void addCommand(CommandLoot command) {
        commandList.add(command);
    }

    /**
     * Returns the list of message to be sent
     *
     * @return The list of Message
     */
    public List<String> getMessageList() {
        return messageList;
    }

    /**
     * Adds the given message to the list of looted messages
     *
     * @param msg The given message to add as loot
     */
    public void addMessage(String msg) {
        messageList.add(msg);
    }

    /**
     * Returns the amount of money in the bundle of loot
     *
     * @return The amount of money that was looted
     */
    public double getMoney() {
        return money;
    }

    /**
     * Adds to the total amount of money in the bundle of loot
     *
     * @param money The additional amount of money to be looted
     */
    public void addMoney(double money) {
        this.money += money;
    }

    /**
     * Sets the amount of money in the bundle of loot
     *
     * @param money The new amount of money to be looted
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * Returns the amount of experience in the bundle of loot
     *
     * @return The amount of experience that was looted
     */
    public int getExp() {
        return exp;
    }

    /**
     * Adds to the total amount of experience in the bundle of loot
     *
     * @param exp The additional amount of experience to be looted
     */
    public void addExp(int exp) {
        this.exp += exp;
    }

    /**
     * Sets the amount of experience in the bundle of loot
     *
     * @param exp The new amount of experience to be looted
     */
    public void setExp(int exp) {
        this.exp = exp;
    }
}
