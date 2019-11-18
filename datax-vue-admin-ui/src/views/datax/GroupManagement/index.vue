<template>
  <div class="app-container">
    <div class="filter-container">
      <el-select v-model="listQuery.groupname" clearable placeholder="分组名" style="width: 200px;">
        <el-option v-for="(item, index) in groupOptions" :key="index" :label="item.label" :value="item.value" />
      </el-select>
      <el-select v-model="listQuery.type" clearable placeholder="类型" style="width: 200px;">
        <el-option v-for="(item, index) in typeOptions" :key="index" :label="item.label" :value="item.value" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="fetchData">
        Search
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        Add
      </el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <!-- <el-table-column label="序号" width="110" align="center">
        <template slot-scope="scope">{{ scope.row.id }}</template>
      </el-table-column> -->
      <el-table-column label="分组名" width="110" align="center">
        <template slot-scope="scope">{{ scope.row.groupname }}
        </template>
      </el-table-column>
      <el-table-column label="类型" width="110" align="center">
        <template slot-scope="scope">{{ scope.row.type }}
        </template>
      </el-table-column>
      <el-table-column label="是否启用" width="110" align="center">
        <template slot-scope="scope">{{ scope.row.status }}
        </template>
      </el-table-column>
      <el-table-column label="说明" width="110" align="center">
        <template slot-scope="scope">{{ scope.row.comments }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            edit
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">
            delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="fetchData" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left">
        <el-form-item label="组名" prop="groupname">
          <el-input v-model="temp.groupname" placeholder="组名" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input v-model="temp.type" placeholder="类型" />
        </el-form-item>
        <el-form-item label="说明">
          <el-input v-model="temp.comments" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Please input" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          Cancel
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          Confirm
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as Api from '@/api/datax-typeGroup'
import waves from '@/directive/waves' // waves directive
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
export default {
  name: 'DataxGroupManagement',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      groupName: 1,
      list: null,
      listLoading: true,
      total: 0,
      listQuery: {
        current: 1,
        size: 10
      },
      dialogFormVisible: false,
      dialogStatus: '',
      temp: {
        id: undefined,
        groupname: '',
        type: 'Default',
        comments: ''
      },
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      rules: {
        groupname: [{ required: true, message: 'this is required', trigger: 'blur' }],
        type: [{ required: true, message: 'this is required', trigger: 'blur' }]
      },
      groupOptions: [],
      typeOptions: []
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      Api.list(this.listQuery).then(response => {
        const { records } = response
        const { total } = response
        this.total = total
        this.list = records
        this.listLoading = false
      })
      this.getGroupOptions()
      this.getTypeOptions()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        groupname: '',
        type: 'Default',
        comments: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          Api.created(this.temp).then(() => {
            this.fetchData()
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          Api.updated(tempData).then(() => {
            this.fetchData()
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Update Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row) {
      console.log('删除')
      const idList = []
      idList.push(row.id)
      // 拼成 idList=xx
      // 多个比较麻烦，这里不处理
      Api.deleted({ idList: row.id }).then(response => {
        this.fetchData()
        this.$notify({
          title: 'Success',
          message: 'Delete Successfully',
          type: 'success',
          duration: 2000
        })
      })
      // const index = this.list.indexOf(row)
    },
    getGroupOptions() {
      Api.getGroupOptions().then(response => {
        this.groupOptions = response
      })
    },
    getTypeOptions() {
      Api.getTypeOptions().then(response => {
        console.log(response)
        this.typeOptions = response
      })
    }
  }
}
</script>
