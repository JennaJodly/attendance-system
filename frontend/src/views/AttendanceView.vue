<template>
  <v-container fluid class="pt-6 px-6">
    <GearLoader  :show="loading" />

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
            <v-btn color="primary" size="small" class="ma-2" @click="markIn" v-if="!inMarked">ATTENDANCE IN</v-btn>
            <v-btn color="primary" size="small" class="ma-2" @click="markOut" v-else-if="inMarked && !outMarked">ATTENDANCE OUT</v-btn>
            <v-btn color="primary" size="small" class="ma-2" disabled v-else>ATTENDANCE MARKED</v-btn>
          </v-row>
          <!-- Always show attendance info if marked -->
          <div v-if="inMarked">
            <div class="mt-2"><strong>Attendance In:</strong> {{ attendanceIn }}</div>
          </div>
          <div v-if="outMarked">
            <div class="mt-2"><strong>Attendance Out:</strong> {{ attendanceOut }}</div>
          </div>
          <div v-if="inMarked && outMarked">
            <div class="mt-2"><strong>Duration Worked:</strong> {{ duration }}</div>
          </div>
        </v-card>
      </v-col>

      <!-- Leave Request Card -->
      <v-col cols="12" md="6">
        <v-card class="pa-4 elevation-4" color="white" style="border: 2px  #D8C4B6 solid;">
          <h3 class="text-h6 font-weight-bold mb-4">Leave Requests</h3>
          <v-row dense>
            <v-col cols="12" sm="6">
              <v-select label="Leave Type" v-model="leavetype" :items="['Sick', 'Casual']" />
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
                   :min="today"
                  @update:model-value="onFromDateChange"
                />
              </v-menu>
            </v-col>

            <v-col cols="6" sm="3">
                <v-menu
                  v-model="toMenu"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                >
                  <template #activator="{ props }">
                    <v-text-field
                      v-model="toDateFormatted"
                      label="To Date"
                      readonly
                      v-bind="props"
                      :disabled="!fromDate" 
                    />
                  </template>
                  <v-date-picker
                    v-model="toDate"
                    @update:model-value="onToDateChange"
                    :min="fromDate" 
                    :disabled="!fromDate"
                  />
                </v-menu>
              </v-col>
            <v-col cols="12">
              <v-textarea v-model="leaveReason" label="Leave Reason" rows="2" />
            </v-col>
            <v-col cols="12" class="text-end">
              <v-btn color="primary" size="small" class="mt-4" @click="submitLeave">Save</v-btn>
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
import { ref, onMounted } from 'vue'
import axios from 'axios'
import GearLoader from '@/components/GearLoader.vue' // import your loader component
import moment from 'moment'
import useToast from '@/composables/useToast'
import useGlobalUtils from '@/composables/useGlobalUtils';

const $globalUtils = useGlobalUtils(); // like global Functions use globalUtils.anyfuction()
const toast = useToast()

import apiClient from '@/utils/api.js';

// const res = await apiClient.get('/api/divisions/dropdown');
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
// const today = new Date().toISOString().split('T')[0]; // format: 'YYYY-MM-DD'


// Add flags for button logic
const inMarked = ref(false)
const outMarked = ref(false)
const loading = ref(false)
const leaveReason = ref('')
const  leavetype = ref('')


// Fetch attendance status on page load
async function fetchAttendanceStatus() {
  const employeeId = localStorage.getItem('employee_id')
  if (!employeeId) return

  loading.value = true
  const todayDate = new Date().toISOString().split('T')[0]
  try {
    const res = await apiClient.get(`/api/attendance/status`, {
      params: {
        employee_id: employeeId,
        in_date: todayDate
      }
    })
    const data = res.data
    // Set flags based on API response
    inMarked.value = !!data.inMarked
    outMarked.value = !!data.outMarked

    // Set attendance times if available (optional, for display)
    if (inMarked.value && data.inTime) {
      attendanceIn.value = new Date(`${todayDate}T${data.inTime}`).toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' })
      inTime = new Date(`${todayDate}T${data.inTime}`)
    } else {
      attendanceIn.value = ''
      inTime = null
    }
    if (outMarked.value && data.outTime) {
      attendanceOut.value = new Date(`${todayDate}T${data.outTime}`).toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' })
      if (inTime) {
        const outTime = new Date(`${todayDate}T${data.outTime}`)
        const diffMs = outTime - inTime
        const diffMins = Math.floor(diffMs / 60000)
        const hours = Math.floor(diffMins / 60)
        const mins = diffMins % 60
        duration.value = `${hours}h ${mins}m`
      }
    } else {
      attendanceOut.value = ''
      duration.value = ''
    }
  } catch (err) {
    console.error('Failed to fetch attendance status', err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchAttendanceStatus()
})

const markIn = async () => {
  loading.value = true
  const employeeId = localStorage.getItem('employee_id')
  if (!employeeId) {
    alert('Employee ID not found in local storage')
    return
  }
inTime = new Date()
  //attendanceIn.value = formatTime(inTime)
  attendanceOut.value = ''
  duration.value = ''
  const now = new Date()
  const inDate = now.toISOString().split('T')[0]           // yyyy-MM-dd
  const attendanceInTime = now.toTimeString().slice(0, 8)  // HH:mm:ss

  const payload = {
    employee: {
      id: employeeId
    },
    active: true,
    inDate: inDate,
    attendanceIn: attendanceInTime,
    attendanceOut: null,
    durationWorked: 0,
    shift: 'Morning',     // You can customize or get from localStorage
    photoPath: ''         // If using camera capture, set image path/base64 here
  }

  try {
    const res = await apiClient.post('/api/attendance/mark-in', payload)
    console.log('Attendance IN Success:', res.data)
    fetchAttendanceStatus() // Refresh status after marking in
     loading.value = false
  } catch (err) {
    console.error('Attendance IN Failed:', err)
  }
}

const markOut = async (outTimeStr) => {
  loading.value = true
  const outTime = new Date()
  const employeeId = localStorage.getItem('employee_id')
  // Set attendanceOut immediately for UI feedback
  attendanceOut.value = outTime.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' })
  const diffMs = outTime - inTime
  const diffMins = Math.floor(diffMs / 60000)
  const hours = Math.floor(diffMins / 60)
  const mins = diffMins % 60
  duration.value = `${hours}h ${mins}m`
  const attendanceoutTime = outTime.toTimeString().slice(0, 8)
  try {
    const payload = {
      durationWorked: diffMins,  // Total minutes worked
      outTime: attendanceoutTime  // e.g., '18:15:00'
    }

    await apiClient.put(`/api/attendance/out/${employeeId}`, payload)
    fetchAttendanceStatus() // Refresh status after marking out
    loading.value = false
    outMarked.value = true // Update flag so UI shows "Attendance Out"
  } catch (error) {
    console.error('Failed to mark OUT', error)
    alert('Error occurred')
  }
}
const submitLeave = async () => {
  console.log("jen")
  loading.value = true
  const employeeId = localStorage.getItem('employee_id')
  if (!employeeId) {
    alert('Employee ID not found in local storage')
    return
  } 
  const payload = {
    employee: {
      id: employeeId
    },
    active: true,
    fromDate: moment(fromDateFormatted.value, 'DD/MM/YYYY').format('YYYY-MM-DD'),
    toDate: moment(toDateFormatted.value, 'DD/MM/YYYY').format('YYYY-MM-DD'),
    leaveReason: leaveReason.value,
    leaveType: leavetype.value
  }

  try {
    const res = await axios.post('http://localhost:9090/api/leave_request/ask_leave', payload)
    console.log('Attendance IN Success:', res.data)
    toast('success', 'Request Sent successfully!');
     fromDateFormatted.value = null
    toDateFormatted.value = null
    leaveReason.value = ''
    leavetype.value = null
     loading.value = false
  } catch (err) {
    console.error('Attendance IN Failed:', err)
  }
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

