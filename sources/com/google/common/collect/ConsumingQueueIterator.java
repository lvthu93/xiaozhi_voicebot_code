package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import java.util.Queue;

class ConsumingQueueIterator<T> extends AbstractIterator<T> {
    public final Queue<T> g;

    public ConsumingQueueIterator(Queue<T> queue) {
        this.g = (Queue) Preconditions.checkNotNull(queue);
    }

    public T computeNext() {
        Queue<T> queue = this.g;
        if (!queue.isEmpty()) {
            return queue.remove();
        }
        this.c = AbstractIterator.State.DONE;
        return null;
    }
}
