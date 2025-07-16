
<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <v-toolbar-title>Millennium Industries</v-toolbar-title>
    </v-app-bar>
    <v-navigation-drawer app permanent>
      <v-list>
        <v-list-item title="Home"></v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>
