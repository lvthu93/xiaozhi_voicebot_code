package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import java.util.ArrayDeque;
import java.util.Iterator;

@Deprecated
public abstract class TreeTraverser<T> {

    public final class BreadthFirstIterator extends UnmodifiableIterator<T> implements PeekingIterator<T> {
        public final ArrayDeque c;

        public BreadthFirstIterator(T t) {
            ArrayDeque arrayDeque = new ArrayDeque();
            this.c = arrayDeque;
            arrayDeque.add(t);
        }

        public boolean hasNext() {
            return !this.c.isEmpty();
        }

        public T next() {
            ArrayDeque arrayDeque = this.c;
            T remove = arrayDeque.remove();
            Iterables.addAll(arrayDeque, TreeTraverser.this.children(remove));
            return remove;
        }

        public T peek() {
            return this.c.element();
        }
    }

    public final class PostOrderIterator extends AbstractIterator<T> {
        public final ArrayDeque<PostOrderNode<T>> g;

        public PostOrderIterator(T t) {
            ArrayDeque<PostOrderNode<T>> arrayDeque = new ArrayDeque<>();
            this.g = arrayDeque;
            arrayDeque.addLast(new PostOrderNode(TreeTraverser.this.children(t).iterator(), t));
        }

        public final T computeNext() {
            while (true) {
                ArrayDeque<PostOrderNode<T>> arrayDeque = this.g;
                if (!arrayDeque.isEmpty()) {
                    PostOrderNode last = arrayDeque.getLast();
                    if (last.b.hasNext()) {
                        T next = last.b.next();
                        arrayDeque.addLast(new PostOrderNode(TreeTraverser.this.children(next).iterator(), next));
                    } else {
                        arrayDeque.removeLast();
                        return last.a;
                    }
                } else {
                    this.c = AbstractIterator.State.DONE;
                    return null;
                }
            }
        }
    }

    public static final class PostOrderNode<T> {
        public final T a;
        public final Iterator<T> b;

        public PostOrderNode(Iterator it, Object obj) {
            this.a = Preconditions.checkNotNull(obj);
            this.b = (Iterator) Preconditions.checkNotNull(it);
        }
    }

    public final class PreOrderIterator extends UnmodifiableIterator<T> {
        public final ArrayDeque c;

        public PreOrderIterator(T t) {
            ArrayDeque arrayDeque = new ArrayDeque();
            this.c = arrayDeque;
            arrayDeque.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(t)));
        }

        public boolean hasNext() {
            return !this.c.isEmpty();
        }

        public T next() {
            ArrayDeque arrayDeque = this.c;
            Iterator it = (Iterator) arrayDeque.getLast();
            T checkNotNull = Preconditions.checkNotNull(it.next());
            if (!it.hasNext()) {
                arrayDeque.removeLast();
            }
            Iterator it2 = TreeTraverser.this.children(checkNotNull).iterator();
            if (it2.hasNext()) {
                arrayDeque.addLast(it2);
            }
            return checkNotNull;
        }
    }

    @Deprecated
    public static <T> TreeTraverser<T> using(final Function<T, ? extends Iterable<T>> function) {
        Preconditions.checkNotNull(function);
        return new TreeTraverser<T>() {
            public Iterable<T> children(T t) {
                return (Iterable) function.apply(t);
            }
        };
    }

    @Deprecated
    public final FluentIterable<T> breadthFirstTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() {
            public UnmodifiableIterator<T> iterator() {
                return new BreadthFirstIterator(t);
            }
        };
    }

    public abstract Iterable<T> children(T t);

    @Deprecated
    public final FluentIterable<T> postOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() {
            public UnmodifiableIterator<T> iterator() {
                TreeTraverser treeTraverser = TreeTraverser.this;
                treeTraverser.getClass();
                return new PostOrderIterator(t);
            }
        };
    }

    @Deprecated
    public final FluentIterable<T> preOrderTraversal(final T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>() {
            public UnmodifiableIterator<T> iterator() {
                TreeTraverser treeTraverser = TreeTraverser.this;
                treeTraverser.getClass();
                return new PreOrderIterator(t);
            }
        };
    }
}
