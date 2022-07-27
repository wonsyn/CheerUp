<template>
  <nav-bar></nav-bar>
  <nav>
    <router-link to="/">Home</router-link> | <router-link to="/login">About</router-link> |
    <router-link to="/signup">SignUp</router-link>
  </nav>
  <router-view />
  <main-footer></main-footer>
</template>
<script>
import NavBar from "@/components/layout/HeaderNavBar.vue";
import MainFooter from "@/components/layout/MainFooter.vue";

export default {
  components: {
    NavBar,
    MainFooter,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
