<template>
  <v-container fluid class="pt-6 px-6">
    <v-row justify="space-between">
      <!-- Attendance Card -->
      <v-col cols="12" md="6">
        <v-card class="pa-4 elevation-4" color="white" style="border: 2px #D8C4B6 solid;">
          <h3 class="text-h6 font-weight-bold mb-2">Welcome</h3>
          <p class="mb-4">{{ today }}</p>
          <v-row align="center" justify="center" class="mb-4">
            <v-img :src="photoUrl" max-width="120" class="rounded-circle" />
          </v-row>
          <v-row justify="center">
            <v-btn color="primary" size="small" class="ma-2" @click="markIn" v-if="!attendanceIn">ATTENDANCE IN</v-btn>
            <v-btn color="primary" size="small" class="ma-2" @click="markOut" v-else-if="attendanceIn && !attendanceOut">ATTENDANCE OUT</v-btn>
            <v-btn color="primary" size="small" class="ma-2" disabled v-else>ATTENDANCE MARKED</v-btn>
          </v-row>
          <div v-if="attendanceIn" class="mt-2"><strong>Attendance In:</strong> {{ attendanceIn }}</div>
          <div v-if="attendanceOut"  class="mt-2"><strong>Attendance Out:</strong> {{ attendanceOut }}</div>
          <div v-if="attendanceIn && attendanceOut" class="mt-2"><strong>Duration Worked:</strong> {{ duration }}</div>
        </v-card>
      </v-col>

      <!-- Leave Request Card -->
      <v-col cols="12" md="6">
        <v-card class="pa-4 elevation-4" color="white" style="border: 2px  #D8C4B6 solid;">
          <h3 class="text-h6 font-weight-bold mb-4">Leave Requests</h3>
          <v-row dense>
            <v-col cols="12" sm="6">
              <v-select label="Leave Type" :items="['Sick', 'Casual', 'Earned']" />
            </v-col>

            <v-col cols="6" sm="3">
              <v-menu v-model="fromMenu" :close-on-content-click="false" transition="scale-transition" offset-y>
                <template #activator="{ props }">
                  <v-text-field
                    v-model="fromDateFormatted"
                    label="From Date"
                    readonly
                    v-bind="props"
                  />
                </template>
                <v-date-picker
                  v-model="fromDate"
                  @update:model-value="onFromDateChange"
                />
              </v-menu>
            </v-col>

            <v-col cols="6" sm="3">
              <v-menu v-model="toMenu" :close-on-content-click="false" transition="scale-transition" offset-y>
                <template #activator="{ props }">
                  <v-text-field
                    v-model="toDateFormatted"
                    label="To Date"
                    readonly
                    v-bind="props"
                  />
                </template>
                <v-date-picker
                  v-model="toDate"
                  @update:model-value="onToDateChange"
                />
              </v-menu>
            </v-col>

            <v-col cols="12">
              <v-textarea label="Leave Reason" rows="2" />
            </v-col>
            <v-col cols="12" class="text-end">
              <v-btn color="primary" size="small" class="mt-4" @click="submitAttendance">Save</v-btn>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>

    <!-- Bottom Row Table -->
    <v-row class="mt-6 align-center">
      <v-col cols="12">
        <v-card class="pa-4 elevation-4" color="white" style="border: 2px #D8C4B6 solid;">
          <v-data-table :headers="tableHeaders" :items="tableRows" class="elevation-0" hide-default-footer>
            <template #item.component="{ item, index }">
              <v-select v-model="item.component" :items="['Component A', 'Component B', 'Component C']" dense hide-details />
            </template>
            <template #item.mechin="{ item, index }">
              <v-select v-model="item.mechin" :items="['Mechin X', 'Mechin Y']" dense hide-details />
            </template>
            <template #item.count="{ item, index }">
              <v-text-field v-model="item.count" dense hide-details style="max-width: 80px;" />
            </template>
            <template #item.actions="{ item, index }">
              <v-btn icon class="mr-2" color="success" size="small" density="compact" @click="addRow" v-if="index === tableRows.length - 1">
                <v-icon small>mdi-plus-circle</v-icon>
              </v-btn>
              <v-btn icon color="error" size="small" density="compact" @click="removeRow(index)" v-if="tableRows.length > 1">
                <v-icon small>mdi-minus-circle</v-icon>
              </v-btn>
            </template>
          </v-data-table>

          <v-row>
            <v-col cols="12" class="text-end">
              <v-btn color="primary" size="small" class="mt-4" @click="submitAttendance">Submit</v-btn>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'

// Today's date display
const today = new Date().toLocaleDateString('en-US', {
  year: 'numeric',
  month: 'short',
  day: 'numeric'
})

// Attendance
const photoUrl = '/photos/sample.jpg'
const attendanceIn = ref('')
const attendanceOut = ref('')
const duration = ref('')
let inTime = null

function formatTime(date) {
  return date.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });
}

function markIn() {
  inTime = new Date()
  attendanceIn.value = formatTime(inTime)
  attendanceOut.value = ''
  duration.value = ''
}

function markOut() {
  if (!inTime) return
  const outTime = new Date()
  attendanceOut.value = formatTime(outTime)
  const diffMs = outTime - inTime
  const diffMins = Math.floor(diffMs / 60000)
  const hours = Math.floor(diffMins / 60)
  const mins = diffMins % 60
  duration.value = `${hours}h ${mins}m`
}

// Table Data
const tableHeaders = [
  { title: 'Component', key: 'component' },
  { title: 'Machine', key: 'mechin' },
  { title: 'Count', key: 'count' },
  { title: 'Actions', key: 'actions', sortable: false }
]

const tableRows = ref([
  { component: '', mechin: '', count: '' }
])

function addRow() {
  tableRows.value.push({ component: '', mechin: '', count: '' })
}

function removeRow(index) {
  if (tableRows.value.length > 1) tableRows.value.splice(index, 1)
}

// Leave Dates
const fromDate = ref(null)
const toDate = ref(null)
const fromMenu = ref(false)
const toMenu = ref(false)
const fromDateFormatted = ref('')
const toDateFormatted = ref('')

function onFromDateChange(val) {
  fromDate.value = val
  fromDateFormatted.value = formatFullDate(val)
  fromMenu.value = false
}

function onToDateChange(val) {
  toDate.value = val
  toDateFormatted.value = formatFullDate(val)
  toMenu.value = false
}

function formatFullDate(date) {
  if (!date) return ''
  const d = new Date(date)
  const day = String(d.getDate()).padStart(2, '0')
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const year = d.getFullYear()
  return `${day}/${month}/${year}`
}

function submitAttendance() {
  console.log('Submitting attendance...')
  console.log('From Date:', fromDate.value)
  console.log('To Date:', toDate.value)
  console.log('Table Rows:', tableRows.value)
}
</script>

<style scoped>
.rounded-circle {
  border-radius: 50%;
  border: 2px solid #ccc;
}
</style>
