<template>
  <div>
    <el-table
      :data="tableData"
      stripe
      border
      height="calc(100vh - 235px)"
      style="width: 100%"
    >
      <el-table-column label="Requests" min-width="150" align="center">
        <el-table-column
          prop="label"
          label="Label"
          sortable
          min-width="150">
          <template v-slot:header="{column}">
            <span>Label</span>
            <i class="el-icon-search" style="margin-left: 8px;cursor: pointer;font-weight: bold;"
               @click="click(column)"></i>
            <el-input v-model="searchLabel"
                      placeholder="请输入 Label 搜索"
                      size="mini"
                      class="search_input"
                      style="width: 100px; margin-left: 5px"
                      v-if="column.showSearch"
                      clearable
                      @clear="filterLabel"
                      @keyup.enter.native="filterLabel"/>
          </template>
        </el-table-column>
      </el-table-column>

      <el-table-column label="Executions" align="center">
        <el-table-column
          prop="samples"
          label="Samples"
          sortable
          width="110"
        />

        <el-table-column
          prop="fail"
          label="FAIL"
          sortable
          align="center"
          min-width="60"
        />

        <el-table-column
          prop="error"
          label="Error%"
          sortable
          align="center"
        />
      </el-table-column>

      <el-table-column label="Response Times(ms)" align="center">
        <el-table-column
          prop="average"
          label="Avg"
          sortable
          min-width="60"
        />
        <el-table-column
          prop="min"
          label="Min"
          sortable
          min-width="60"
        />
        <el-table-column
          prop="max"
          label="Max"
          sortable
          min-width="60"
        />
        <el-table-column
          prop="median"
          label="Med"
          sortable
          min-width="60"
        />
        <el-table-column
          prop="tp90"
          label="90%"
          sortable
          min-width="60"
        />
        <el-table-column
          prop="tp95"
          label="95%"
          sortable
          min-width="60"
        />
        <el-table-column
          prop="tp99"
          label="99%"
          sortable
          min-width="60"
        />
      </el-table-column>

      <el-table-column label="Throughput">
        <el-table-column
          prop="transactions"
          label="Trans/s"
          sortable
          width="100"
        />
      </el-table-column>

      <el-table-column label="NetWork(KB/sec)" align="center">
        <el-table-column
          prop="received"
          label="Recd"
          sortable
          align="center"
          width="100"
        />
        <el-table-column
          prop="sent"
          label="Sent"
          sortable
          align="center"
          width="100"
        />
      </el-table-column>

    </el-table>
  </div>
</template>

<script>
export default {
  name: "RequestStatistics",
  data() {
    return {
      tableData: [],
      originalData: [],
      id: '',
      searchLabel: '',
      showSearch: false,
      showBtn: true,
    }
  },
  methods: {
    initTableData() {
      this.$get("/performance/report/content/" + this.id).then(res => {
        this.tableData = res.data.data;
        this.originalData = res.data.data;
      }).catch(() => {
        this.tableData = [];
      })
    },
    click(column) {
      this.searchLabel = '';
      this.tableData = this.originalData;
      this.$set(column, 'showSearch', !column.showSearch);
    },
    filterLabel() {
      this.tableData = this.searchLabel ? this.originalData.filter(this.createFilter(this.searchLabel)) : this.originalData;
    },
    createFilter(queryString) {
      return item => {
        return (item.label.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
      };
    },
  },
  watch: {
    report: {
      handler(val) {
        if (!val.status || !val.id) {
          return;
        }
        let status = val.status;
        this.id = val.id;
        if (status === "Completed" || status === "Running") {
          this.initTableData();
        } else {
          this.tableData = [];
        }
      },
      deep: true
    }
  },
  props: ['report']
}
</script>

<style scoped>
.search_input >>> .el-input__inner {
  border-radius: 50px;
}
</style>
