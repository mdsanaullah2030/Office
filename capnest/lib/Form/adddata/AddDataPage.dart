import 'package:capnest/Form/dashboard/DashboardView.dart';
import 'package:capnest/Form/model/FormModel.dart';
import 'package:capnest/Form/servics/FormService.dart';
import 'package:flutter/material.dart';

class AddDataPage extends StatefulWidget {
  final FormModel? existingData;
  const AddDataPage({super.key, this.existingData});

  @override
  State<AddDataPage> createState() => _AddDataPageState();
}

class _AddDataPageState extends State<AddDataPage> {
  final _formKey = GlobalKey<FormState>();
  final FormService _service = FormService();

  late TextEditingController fullNameController;
  late TextEditingController emailController;
  late TextEditingController phoneController;
  late TextEditingController addressController;
  late TextEditingController nationalIdController;
  DateTime? selectedDate;

  String? selectedGender;  // For dropdown

  @override
  void initState() {
    super.initState();
    final data = widget.existingData;
    fullNameController = TextEditingController(text: data?.fullName ?? '');
    emailController = TextEditingController(text: data?.email ?? '');
    phoneController = TextEditingController(text: data?.phoneNumber ?? '');
    addressController = TextEditingController(text: data?.address ?? '');
    nationalIdController = TextEditingController(text: data?.nationalId ?? '');
    selectedDate = data?.dateOfBirth ?? DateTime.now();
    selectedGender = data?.gender ?? 'Male';  // default to Male if null
  }

  Future<void> saveForm() async {
    if (_formKey.currentState!.validate()) {
      FormModel formData = FormModel(
        id: widget.existingData?.id,
        fullName: fullNameController.text,
        email: emailController.text,
        phoneNumber: phoneController.text,
        address: addressController.text,
        gender: selectedGender ?? 'Male',
        dateOfBirth: selectedDate!,
        nationalId: nationalIdController.text,
      );

      bool success = widget.existingData == null
          ? await _service.saveForm(formData)
          : await _service.updateForm(formData);

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(success ? 'Saved' : 'Failed')),
      );

      if (success) {
        Navigator.pushAndRemoveUntil(
          context,
          MaterialPageRoute(builder: (context) => const DashboardView()),
              (route) => false,
        );
      }
    }
  }

  Future<void> pickDate() async {
    DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedDate ?? DateTime(2000),
      firstDate: DateTime(1900),
      lastDate: DateTime(2100),
    );
    if (picked != null) {
      setState(() => selectedDate = picked);
    }
  }

  Widget buildTextField(String label, TextEditingController controller, IconData icon) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: TextFormField(
        controller: controller,
        decoration: InputDecoration(
          labelText: label,
          border: const OutlineInputBorder(),
          prefixIcon: Icon(icon),
        ),
        validator: (value) => value == null || value.isEmpty ? 'Enter $label' : null,
      ),
    );
  }

  Widget buildGenderDropdown() {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: DropdownButtonFormField<String>(
        value: selectedGender,
        decoration: const InputDecoration(
          labelText: 'Gender',
          border: OutlineInputBorder(),
          prefixIcon: Icon(Icons.person),
        ),
        items: const [
          DropdownMenuItem(value: 'Male', child: Text('Male')),
          DropdownMenuItem(value: 'Female', child: Text('Female')),
        ],
        onChanged: (value) {
          setState(() {
            selectedGender = value;
          });
        },
        validator: (value) => value == null || value.isEmpty ? 'Select Gender' : null,
      ),
    );
  }

  @override
  void dispose() {
    fullNameController.dispose();
    emailController.dispose();
    phoneController.dispose();
    addressController.dispose();
    nationalIdController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(widget.existingData == null ? 'Add Data' : 'Edit Data')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [
              buildTextField("Full Name", fullNameController, Icons.person),
              buildTextField("Email", emailController, Icons.email),
              buildTextField("Phone Number", phoneController, Icons.phone),
              buildTextField("Address", addressController, Icons.home),
              buildGenderDropdown(),  // <-- Dropdown with icon
              buildTextField("National ID", nationalIdController, Icons.badge),
              const SizedBox(height: 10),
              Row(
                children: [
                  const Spacer(),
                  ElevatedButton(
                    onPressed: pickDate,
                    child: const Text("Pick Date"),
                  ),
                ],
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: saveForm,
                child: Text(widget.existingData == null ? "Submit" : "Update"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
