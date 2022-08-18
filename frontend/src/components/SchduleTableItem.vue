<template>
  <tr style="cursor: pointer">
    <th scope="row">{{ index }}</th>
    <td>{{ scheduleTitle }}</td>
    <td>{{ scheduleCompany }}</td>
    <td>{{ scheduleDate }}</td>
    <td>{{ scheduleMemo }}</td>
  </tr>
</template>

<script>
export default {
  props: {
    scheduleId: Number,
    userId: Number,
    scheduleTitle: String,
    scheduleCompany: String,
    scheduleDate: String,
    scheduleMemo: String,
    index: Number,
  },
  created() {
    console.log(this.scheduleId);
  },
};
</script>

<style></style>
