import { Component, OnInit } from '@angular/core';
import {TableView} from "NG2TableView";


export const PageTableColumns:Array<ColumnIfc> = new TableColumns()
    .addCol(new TableColumn()
        .setTitle("index")
        .setName("index")
        .setSort(true)
    )
    .addCol(new TableColumn()
        .setTitle("Editable name ")
        .setName("name")
        .setTemplate(require("./custom-template.html"))
        .setSort(true)
    )