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
import com.belatrixsf.allstars.utils.SearchingHelper;
import com.belatrixsf.allstars.utils.exceptions.InvalidPageException;

import java.util.List;

/**
 * Created by gyosida on 6/8/16.
 */
public class SearchingEngine<T extends SearchingHelper.Searchable> {

    private PaginationEngine<T> employeeSearchPagination = new PaginationEngine<>();
    private String searchText;
    private List<T> items;

    public SearchingEngine(List<T> items) {
        if (items == null) {
            throw new RuntimeException("items cannot be null");
        }
        this.items = items;
        employeeSearchPagination.setItems(this.items);
    }

    public PaginatedResponse<T> search(String text, Integer page) throws InvalidPageException {
        if (!keepSearchingTheSame(text)) {
            List<T> items = text == null? this.items : SearchingHelper.search(this.items, text);
            employeeSearchPagination.setItems(items);
        }
        this.searchText = text;
        return employeeSearchPagination.getItems(page);
    }

    public void setItemsPerPage(int itemsPerPage) {
        employeeSearchPagination.setItemsPerPage(itemsPerPage);
    }

    private boolean keepSearchingTheSame(String searchText) {
        return this.searchText != null && searchText != null && this.searchText.equals(searchText);
    }

}
