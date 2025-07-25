<template>
  <v-container fluid class="pt-6 px-6">
    <GearLoader  :show="loading" />

    <v-dialog v-model="showComponentDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Component</v-card-title>
        <v-card-text>
          <v-text-field
            label="Name *"
            v-model="newComponent.name"
            dense
            required
          />
          <v-textarea
            label="Description"
            v-model="newComponent.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showComponentDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveComponent">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="showMachineDialog" max-width="500px" persistent>
      <v-card>
        <v-card-title class="text-h6">Add Machine</v-card-title>
        <v-card-text>
          <v-text-field
            label="Name *"
            v-model="newMachine.name"
            dense
            required
          />
          <v-textarea
            label="Description"
            v-model="newMachine.description"
            dense
          />
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn text @click="showMachineDialog = false">Cancel</v-btn>
          <v-btn color="primary" @click="saveMachine">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

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
            <v-btn 
              color="primary" 
              size="small" 
              class="ma-2" 
              @click="markOut" 
              v-else-if="inMarked && !outMarked"
              :disabled="!isOutEnabled"
            >
              ATTENDANCE OUT
              <span v-if="!isOutEnabled" class="ml-2">({{ remainingTime }})</span>
            </v-btn>
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
          <!-- Show time remaining until out is enabled -->
          <div v-if="inMarked && !outMarked && !isOutEnabled" class="mt-2 text-orange">
            <strong>Time until out enabled:</strong> {{ remainingTime }}
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
      <v-data-table
        :headers="tableHeaders"
        :items="tableRows"
        class="elevation-0"
        hide-default-footer
      >
       <!-- Component column with + button (more space for select, tighter button spacing) -->
        <template #item.component="{ item, index }">
          <div class="d-flex align-center">
            <v-select
              v-model="item.component"
              :items="components"
              item-title="name"
              item-value="id"
              dense
              hide-details
              return-object
              style="max-width: 250px;"
            />
            <v-btn
              icon
              class="ml-2"
              color="success"
              size="x-small"
              density="comfortable"
              @click="showComponentDialog = true"
            >
              <v-icon small>mdi-plus</v-icon>
            </v-btn>
          </div>
        </template>


    <template #item.mechin="{ item, index }">
  <div class="d-flex align-center">
    <v-select
      v-model="item.mechin"
      :items="machines"
     item-title="name"
      item-value="id"
      dense
      hide-details
      return-object
      style="max-width: 250px;"
    />
    <v-btn
      icon
      class="ml-2"
      color="success"
      size="x-small"
      density="comfortable"
      @click="showMachineDialog = true"
    >
      <v-icon small>mdi-plus</v-icon>
    </v-btn>
  </div>
</template>

        <!-- Count column -->
        <template #item.count="{ item, index }">
          <v-text-field
            v-model="item.count"
            dense
            hide-details
            style="max-width: 80px;"
          />
        </template>

        <!-- Actions column -->
        <template #item.actions="{ item, index }">
          <v-btn
            icon
            class="mr-2"
            color="success"
            size="small"
            density="compact"
            @click="addRow"
            v-if="index === tableRows.length - 1"
          >
            <v-icon small>mdi-plus-circle</v-icon>
          </v-btn>
          <v-btn
            icon
            color="error"
            size="small"
            density="compact"
            @click="removeRow(index)"
            v-if="tableRows.length > 1"
          >
            <v-icon small>mdi-minus-circle</v-icon>
          </v-btn>
        </template>
      </v-data-table>

      <!-- Submit Button -->
      <v-row>
        <v-col cols="12" class="text-end">
          <v-btn
            color="primary"
            size="small"
            class="mt-4"
            @click="submitAttendance"
          >
            Submit
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-col>
</v-row>

  </v-container>
</template>

<script setup>
import { ref, onMounted, computed, onUnmounted, reactive } from 'vue'
import axios from 'axios'
import GearLoader from '@/components/GearLoader.vue' // import your loader component
import moment from 'moment'
import useToast from '@/composables/useToast'
import useGlobalUtils from '@/composables/useGlobalUtils';

const $globalUtils = useGlobalUtils(); // like global Functions use globalUtils.anyfuction()
const toast = useToast()

import apiClient from '@/utils/api.js';

// Today's date display
const today = new Date().toLocaleDateString('en-US', {
  year: 'numeric',
  month: 'short',
  day: 'numeric'
})

const newComponent = reactive({ name: "", description: "" });
const newMachine = reactive({ name: "", description: "" });

// Attendance
const photoUrl = '/photos/sample.jpg'
const attendanceIn = ref('')
const attendanceOut = ref('')
const duration = ref('')
const inTime = ref(null) // Changed to ref for reactivity
const currentTime = ref(new Date()) // Track current time for countdown
const components = ref([]);
const machines = ref([]);


// Add flags for button logic
const inMarked = ref(false)
const outMarked = ref(false)
const loading = ref(false)
const leaveReason = ref('')
const leavetype = ref('')
const showComponentDialog = ref(false);
const showMachineDialog = ref(false);

// Timer for updating current time
let timeInterval = null

// Computed property to check if attendance out is enabled (after 8 hours)
const isOutEnabled = computed(() => {
  if (!inMarked.value || !inTime.value) return false
  
  const now = currentTime.value
  const diffMs = now - inTime.value
  const diffHours = diffMs / (1000 * 60 * 60)
  
  return diffHours >= 8
})

// Computed property to show remaining time until out is enabled
const remainingTime = computed(() => {
  if (!inMarked.value || !inTime.value || isOutEnabled.value) return ''
  
  const now = currentTime.value
  const diffMs = now - inTime.value
  const eightHoursMs = 8 * 60 * 60 * 1000
  const remainingMs = eightHoursMs - diffMs
  
  if (remainingMs <= 0) return ''
  
  const hours = Math.floor(remainingMs / (1000 * 60 * 60))
  const minutes = Math.floor((remainingMs % (1000 * 60 * 60)) / (1000 * 60))
  const seconds = Math.floor((remainingMs % (1000 * 60)) / 1000)
  
  return `${hours}h ${minutes}m ${seconds}s`
})

const fetchComponents = async () => {
  const res = await apiClient.get('/api/components/dropdown');
  components.value = res.data;
};
onMounted(fetchComponents);

const fetchMachines = async () => {
  const res = await apiClient.get('/api/machines/dropdown');
  machines.value = res.data;
};
onMounted(fetchMachines);


const addMachine = () => {
  showMachineDialog.value = true;
};

// Start timer to update current time every second
function startTimer() {
  timeInterval = setInterval(() => {
    currentTime.value = new Date()
  }, 1000)
}

const saveMachine = async () => {
  if (!newMachine.name) {
    toast('error', 'Name is required');
    return;
  }

  try {
    // POST to save new machine
    const resSave = await apiClient.post('/api/machines', newMachine);
    toast('success', 'Machine added successfully');
    showMachineDialog.value = false;

    // Refresh machine list
    const res = await apiClient.get('/api/machines/dropdown');
    machines.value = res.data;

    // Assign newly added machine to last row
    const newItem = res.data.find(item => item.id === resSave.data.id);
    if (tableRows.length > 0 && newItem) {
      tableRows[tableRows.length - 1].mechin = newItem.id;
    }

    // Reset form
    Object.assign(newMachine, { name: '', description: '' });
  } catch (err) {
    console.error("Error adding machine", err);
    toast('error', 'Failed to add machine');
  }
};


const saveComponent = async () => {
  if (!newComponent.name) {
    toast('error', 'Name is required');
    return;
  }

  try {
    const resSave = await apiClient.post('/api/components', newComponent);
    toast('success', 'Component added successfully');
    showComponentDialog.value = false;

    // Refresh component list
    const res = await apiClient.get('/api/components/dropdown');
    components.value = res.data;

    const newItem = res.data.find(item => item.id === resSave.data.id);

    // ✅ Safely assign to last row
    if (tableRows.length > 0 && newItem) {
      tableRows[tableRows.length - 1].component = newItem;
    }

    console.log("New component added:", newItem);

    // Reset form
    Object.assign(newComponent, { name: '', description: '' });
  } catch (err) {
    console.error("Error adding component", err);
    toast('error', 'Failed to add component');
  }
};

// Stop timer
function stopTimer() {
  if (timeInterval) {
    clearInterval(timeInterval)
    timeInterval = null
  }
}

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

    // Set attendance times if available
    if (inMarked.value && data.inTime) {
      const inDateTime = new Date(`${todayDate}T${data.inTime}`)
      attendanceIn.value = inDateTime.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' })
      inTime.value = inDateTime
    } else {
      attendanceIn.value = ''
      inTime.value = null
    }
    
    if (outMarked.value && data.outTime) {
      attendanceOut.value = new Date(`${todayDate}T${data.outTime}`).toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' })
      if (inTime.value) {
        const outTime = new Date(`${todayDate}T${data.outTime}`)
        const diffMs = outTime - inTime.value
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
  startTimer() // Start the timer when component mounts
})

onUnmounted(() => {
  stopTimer() // Clean up timer when component unmounts
})

const markIn = async () => {
  loading.value = true
  const employeeId = localStorage.getItem('employee_id')
  if (!employeeId) {
    alert('Employee ID not found in local storage')
    loading.value = false
    return
  }

  const now = new Date()
  inTime.value = now
  attendanceOut.value = ''
  duration.value = ''
  
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
    await fetchAttendanceStatus() // Refresh status after marking in
  } catch (err) {
    console.error('Attendance IN Failed:', err)
    toast('error', 'Failed to mark attendance in')
  } finally {
    loading.value = false
  }
}

const markOut = async () => {
  if (!isOutEnabled.value) {
    toast('warning', 'You can only mark out after completing 8 hours of work')
    return
  }

  loading.value = true
  const outTime = new Date()
  const employeeId = localStorage.getItem('employee_id')
  
  // Set attendanceOut immediately for UI feedback
  attendanceOut.value = outTime.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' })
  
  const diffMs = outTime - inTime.value
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
    await fetchAttendanceStatus() // Refresh status after marking out
    toast('success', 'Attendance out marked successfully!')
  } catch (error) {
    console.error('Failed to mark OUT', error)
    toast('error', 'Failed to mark attendance out')
  } finally {
    loading.value = false
  }
}

const submitLeave = async () => {
  loading.value = true
  const employeeId = localStorage.getItem('employee_id')
  if (!employeeId) {
    alert('Employee ID not found in local storage')
    loading.value = false
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
    console.log('Leave request Success:', res.data)
    toast('success', 'Request Sent successfully!');
    fromDateFormatted.value = null
    toDateFormatted.value = null
    leaveReason.value = ''
    leavetype.value = null
  } catch (err) {
    console.error('Leave request Failed:', err)
    toast('error', 'Failed to submit leave request')
  } finally {
    loading.value = false
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

const submitAttendance = async () => {
  const employeeId = localStorage.getItem('employee_id')
  console.log('Submitting attendance for employee ID:', tableRows)
  try {
    const payload = tableRows.value.map(row => ({
      employee: { id: employeeId },  // Replace with actual employee ID
      component: { id: row.component.id },
      machine: { id: row.mechin.id },
      count: row.count,
      date: new Date().toISOString().slice(0, 10),
      // shift: selectedShift || '-'  // Replace with actual shift selection
    }));

    // POST each row or send as a batch (backend must support array if doing batch)
    for (const entry of payload) {
      await apiClient.post('/api/production-entries', entry);
    }

    toast('success', 'Production data submitted successfully');
    // Reset table rows after submission
    tableRows.value = [{ component: '', mechin: '', count: '' }];

  } catch (error) {
    console.error('Submit failed', error);
    toast('error', 'Failed to submit production entries');
  }
};

</script>

<style scoped>
.rounded-circle {
  border-radius: 50%;
  border: 2px solid #ccc;
}

.text-orange {
  color: #ff9800;
}
</style>