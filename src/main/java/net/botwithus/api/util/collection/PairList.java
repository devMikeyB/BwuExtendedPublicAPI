package net.botwithus.api.util.collection;

import net.botwithus.rs3.util.collection.Pair;

import java.util.*;

public class PairList<L, R> extends ArrayList<net.botwithus.rs3.util.collection.Pair<L, R>> {
    public PairList() {
        super();
    }

    public PairList(int initialCapacity) {
        super(initialCapacity);
    }

    public PairList(net.botwithus.rs3.util.collection.Pair<L, R> var1) {
        this.add(var1);
    }

    public PairList(net.botwithus.rs3.util.collection.Pair<L, R>[] pairs) {
        super(Arrays.asList(pairs));
    }

    public PairList(Collection<net.botwithus.rs3.util.collection.Pair<L, R>> pairs) {
        super(pairs);
    }

    public PairList(Set<Map.Entry<L, R>> mapEntries) {
        super(mapEntries.size());
        for (Map.Entry<L, R> entry : mapEntries) {
            this.add(entry.getKey(), entry.getValue());
        }
    }

    public void add(L left, R right) {
        this.add(new net.botwithus.rs3.util.collection.Pair<>(left, right));
    }

    public boolean contains(L left, R right) {
        return this.contains(new net.botwithus.rs3.util.collection.Pair<>(left, right));
    }

    public List<R> getRights(L left) {
        List<R> list = new ArrayList<>(this.size());
        for (net.botwithus.rs3.util.collection.Pair<L, R> pair : this) {
            if (pair.getLeft().equals(left)) {
                list.add(pair.getRight());
            }
        }
        return list;
    }

    public List<R> getRights() {
        List<R> list = new ArrayList<>(this.size());
        for (net.botwithus.rs3.util.collection.Pair<L, R> pair : this) {
            list.add(pair.getRight());
        }
        return list;
    }

    public List<L> getLefts(R right) {
        List<L> list = new ArrayList<>(this.size());
        for (net.botwithus.rs3.util.collection.Pair<L, R> pair : this) {
            if (pair.getRight().equals(right)) {
                list.add(pair.getLeft());
            }
        }
        return list;
    }

    public List<L> getLefts() {
        List<L> list = new ArrayList<>(this.size());
        for (Pair<L, R> pair : this) {
            list.add(pair.getLeft());
        }
        return list;
    }
}