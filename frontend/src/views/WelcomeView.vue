<template>
  <v-container fluid class="welcome-wrapper">
    <!-- Background Logo (blurred) -->
    <div class="logo-background"></div>

    <!-- Foreground content -->
    <div class="text-center content-overlay">
      <v-icon large color="black" class="mb-4" @click="startCamera" style="cursor: pointer;">
  mdi-fingerprint
</v-icon>

<div v-if="showCamera" class="mt-4 d-flex flex-column align-center">
  <video ref="video" autoplay playsinline width="320" height="240" />
  <v-btn color="success" class="mt-3" @click="capturePhoto">Capture Photo</v-btn>
</div>


      <!-- Captured Image Preview -->
      <!-- <div v-if="capturedImage" class="mt-4">
        <img :src="capturedImage" alt="Captured" width="200" />
        <div class="mt-2">
          <v-btn color="green" @click="Attendance('IN')">Mark Attendance</v-btn>
          <v-btn color="red" class="ml-2" @click="markAttendance('OUT')">Attendance Out</v-btn>
        </div>
      </div> -->
    </div>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      showCamera: false,
      capturedImage: null,
    };
  },
  methods: {
    async startCamera() {
  this.showCamera = true;

  // Wait until the DOM has updated and video element is rendered
  this.$nextTick(async () => {
    const video = this.$refs.video;

    try {
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      video.srcObject = stream;
    } catch (err) {
      alert("Camera access denied or not available.");
      console.error("Camera error:", err);
    }
  });
},
    capturePhoto() {
      const video = this.$refs.video;
      const canvas = document.createElement("canvas");
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;
      const context = canvas.getContext("2d");
      context.drawImage(video, 0, 0);
      this.capturedImage = canvas.toDataURL("image/jpeg");
      video.srcObject.getTracks().forEach((track) => track.stop());
      this.showCamera = false;
      this.$router.push('/attendance');
    },
    markAttendance(type) {
      const payload = {
        employeeId: "demo-emp-1",
        type: type,
        photo: this.capturedImage,
      };

      fetch("http://localhost:8080/attendance", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      })
        .then((res) => {
          if (res.ok) alert(`${type} marked successfully!`);
          else throw new Error("Error marking attendance");
        })
        .catch((err) => alert("Failed: " + err.message));
    },
  },
};
</script>

<style scoped>
.welcome-wrapper {
  position: relative;
  height: 100vh;
  background-color: #999; /* fallback */
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-background {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  height: 400px;
  transform: translate(-50%, -50%);
  background-image: url('@/assets/logo.png');
  background-repeat: no-repeat;
  background-position: center;
  background-size: contain;
  filter: blur(0px) opacity(0.6);
  z-index: 1;
}

.content-overlay {
  position: relative;
  z-index: 10;
  background-color: rgba(255, 255, 255, 0.85);
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
}
video {
  border: 2px solid #ccc;
  border-radius: 8px;
}
</style>
