package com.example.abako_bank.models.models2;

public class Pagination {

    public int totalItems;
    public int totalPages;
    public int currentPage;

    public Pagination( int t, int tp, int cp  )
    {
        totalItems = t;
        totalPages = tp;
        currentPage = cp;

    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
