export default function StatusBadge({ status }) {
  const color =
    status === "RECALL" ? "red" : status === "SAFE" ? "green" : "grey";

  return (
    <span
      style={{
        padding: "5px 10px",
        backgroundColor: color,
        color: "white",
        borderRadius: 5,
      }}
    >
      {status}
    </span>
  );
}
