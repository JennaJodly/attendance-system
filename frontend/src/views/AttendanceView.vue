
<template>
  <v-container>
    <h2>Welcome</h2>
    <v-img :src="photoUrl" max-width="300"></v-img>
    <v-btn color="success" class="ma-2">Attendance IN</v-btn>
    <v-btn color="error" class="ma-2">Attendance OUT</v-btn>
  </v-container>
</template>

<script setup>
const photoUrl = '/photos/sample.jpg'
</script>
