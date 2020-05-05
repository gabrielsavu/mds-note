package ro.mds.note.helper;

import android.widget.AbsListView;

public abstract class LazyLoader implements AbsListView.OnScrollListener {

    private static final int DEFAULT_THRESHOLD = 10;

    private boolean loading = true;
    private int previousTotal = 0;
    private int threshold = DEFAULT_THRESHOLD;

    public LazyLoader() {
    }

    public LazyLoader(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && ((firstVisibleItem + visibleItemCount) >= (totalItemCount - threshold))) {
            loading = true;

            loadMore(view, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }
    }

    public abstract void loadMore(AbsListView view, int firstVisibleItem,
                                  int visibleItemCount, int totalItemCount);
}