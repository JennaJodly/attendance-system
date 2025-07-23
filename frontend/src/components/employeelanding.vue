<template>
  <div class="ma-6">
    <v-row class="d-flex justify-space-between align-center mb-4">
      <h2>Employee</h2>
      <v-btn color="primary" size="small" @click="goToAdd">Add</v-btn>
    </v-row>

    <v-data-table
      :headers="headers"
      :items="employees"
      :items-per-page="5"
      class="elevation-5"
    >
      <template #item.photo="{ item }">
        <v-avatar size="40">
          <v-img :src="item.photo" alt="Photo" />
        </v-avatar>
      </template>

      <template #item.shift="{ item }">
        <span>{{ formatShift(item.shift) }}</span>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import axios from 'axios';
import apiClient from '@/utils/api';

// const res = await apiClient.get('/api/divisions/dropdown');

export default {
  name: 'EmployeeLanding',
  data() {
    return {
      employees: [],
      headers: [
        { title: 'S No', key: 'id' },
        { title: 'Name', key: 'name' },
        { title: 'Email', key: 'email' },
        { title: 'Phone', key: 'phone' },
        { title: 'Photo', key: 'photo' },
        { title: 'Designation', key: 'designation' },
        { title: 'Shift', key: 'shift' }
      ]
    };
  },
  methods: {
    goToAdd() {
      this.$router.push('/employee_add');
    },
    async fetchEmployees() {
      try {
        const response = await apiClient.get('/api/users/all');
        this.employees = response.data;
      } catch (error) {
        console.error('Error fetching employees:', error);
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
