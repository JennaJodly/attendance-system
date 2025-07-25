<template>
  <div class="ma-6">
     <GearLoader  :show="loading" />
    <v-row class="d-flex justify-space-between align-center mb-4">
      <h2>Employee</h2>
      <v-btn color="primary" size="small" @click="goToAdd">Add</v-btn>
    </v-row>

    <v-data-table
      :headers="headers"
      :items="employees"
      :items-per-page="10"
      class="elevation-5"
    >
      <template #item.photo="{ item }">
        <v-avatar size="35">
          <v-img :src="item.photo" />
        </v-avatar>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import axios from 'axios';
import apiClient from '@/utils/api.js';
import GearLoader from '@/components/GearLoader.vue' // import your loader component
// const res = await apiClient.get('/api/divisions/dropdown');
const defaultAvatar = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png";
export default {
  name: 'EmployeeLanding',
   components: {
    GearLoader
  },
  data() {
    return {
      loading: false,
      employees: [],
      headers: [
        { title: 'S No', key: 'id' },
        { title: 'Photo', key: 'photo', sortable: false },
        { title: 'Name', key: 'name' },
        {title: 'Emp No', key: 'empNo'},
        { title: 'Branch', key: 'branch' },
        { title: 'Email', key: 'email' },
        { title: 'Phone', key: 'officialNo' },
        { title: 'Department', key: 'department' },
        { title: 'Designation', key: 'designation' },
        // { title: 'Shift', key: 'shift' }
      ]
    };
  },
  methods: {
    goToAdd() {
      this.$router.push('/employee_add');
    },
    async fetchEmployees() {
        this.loading = true
      try {
        const response = await apiClient.get('/api/employee/all');
         this.employees = response.data.map((emp, index) => ({
        id: index + 1, // For S No
        name: `${emp.firstName} ${emp.lastName}`,
        email: emp.email || '',
        officialNo: emp.officialNo || '-',        
        branch: emp.branch || '-',
        department: emp.department || '-',
        designation: emp.designation || '',
        empNo: emp.empNo || '-',
        photo: emp.photo || defaultAvatar
      }));
        console.log(this.employees);
      } catch (error) {
        console.error('Error fetching employees:', error);
      } finally {
        this.loading = false
      }
    },
    formatShift(shift) {
      if (!shift) return '-';
      try {
        const s = typeof shift === 'string' ? JSON.parse(shift) : shift;
        return `Start: ${s.startTime}, End: ${s.endTime}`;
      } catch {
        return shift;
      }
    }
  },
  mounted() {
    this.fetchEmployees();
  }
};
</script>
