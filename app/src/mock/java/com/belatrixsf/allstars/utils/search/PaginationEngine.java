/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrixsf.allstars.utils.search;

import com.belatrixsf.allstars.networking.retrofit.responses.PaginatedResponse;
import com.belatrixsf.allstars.utils.exceptions.InvalidPageException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyosida on 6/7/16.
 */
public class PaginationEngine<T> {

    private static final String FAKE_URL = "www.fakeallstarshost.com?page=%s";

    private int itemsPerPage = 10;
    private List<T> items;
    private Integer next;
    private int offset;
    private int end;

    public PaginatedResponse<T> getItems(Integer page) throws InvalidPageException {
        if (page == null || page < 0) {
            throw new InvalidPageException();
        }
        offset = calculateOffset(page);
        end = calculateEnd();
        calculateNextPage();
        return createResponse(getResults(offset, end));
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    private int calculateOffset(int page) {
        return page * itemsPerPage;
    }

    private int calculateEnd() {
        int count = items.size();
        int end = offset + itemsPerPage;
        return  (end >= count? count : end);
    }

    private PaginatedResponse<T> createResponse(List<T> results) {
        PaginatedResponse<T> response = new PaginatedResponse<>();
        response.setCount(results.size());
        response.setResults(results);
        response.setNext(next != null? String.format(FAKE_URL, next) : null);
        return response;
    }

    private List<T> getResults(int offset, int end) {
        int count = items.size();
        if (offset >= 0 && offset < count && end <= count) {
            return items.subList(offset, end);
        }
        return new ArrayList<>();
    }

    private void calculateNextPage() {
        next = end + 1 < items.size()? next == null? 1 : next++ : null;
    }

}
