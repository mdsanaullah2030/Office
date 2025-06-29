import 'package:capnest/Form/dashboard/DashboardView.dart';
import 'package:capnest/Form/model/FormModel.dart';
import 'package:capnest/Form/servics/FormService.dart';

import 'package:flutter/material.dart';


class Dataupdate extends StatefulWidget {
  final FormModel existingData;
  const Dataupdate({super.key, required this.existingData});

  @override
  State<Dataupdate> createState() => _DataupdateState();
}

class _DataupdateState extends State<Dataupdate> {
  final _formKey = GlobalKey<FormState>();
  final FormService _service = FormService();

  late TextEditingController fullNameController;
  late TextEditingController emailController;
  late TextEditingController phoneController;
  late TextEditingController addressController;
  late TextEditingController genderController;
  late TextEditingController nationalIdController;
  DateTime? selectedDate;

  @override
  void initState() {
    super.initState();
    final data = widget.existingData;
    fullNameController = TextEditingController(text: data.fullName);
    emailController = TextEditingController(text: data.email);
    phoneController = TextEditingController(text: data.phoneNumber);
    addressController = TextEditingController(text: data.address);
    genderController = TextEditingController(text: data.gender);
    nationalIdController = TextEditingController(text: data.nationalId);
    selectedDate = data.dateOfBirth;
  }

  Future<void> updateForm() async {
    if (_formKey.currentState!.validate()) {
      FormModel updatedData = FormModel(
        id: widget.existingData.id,
        fullName: fullNameController.text,
        email: emailController.text,
        phoneNumber: phoneController.text,
        address: addressController.text,
        gender: genderController.text,
        dateOfBirth: selectedDate!,
        nationalId: nationalIdController.text,
      );

      bool success = await _service.updateForm(updatedData);
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
            success
                ? '✅ Data updated successfully!'
                : '❌ Failed to update data.',
          ),
          backgroundColor: success ? Colors.green : Colors.red,
        ),
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

  Widget buildTextField(String label, TextEditingController controller) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: TextFormField(
        controller: controller,
        decoration: InputDecoration(
          labelText: label,
          border: OutlineInputBorder(),
        ),
        validator: (value) => value == null || value.isEmpty ? 'Enter $label' : null,
      ),
    );
  }

  @override
  void dispose() {
    fullNameController.dispose();
    emailController.dispose();
    phoneController.dispose();
    addressController.dispose();
    genderController.dispose();
    nationalIdController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Update Data")),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [
              buildTextField("Full Name", fullNameController),
              buildTextField("Email", emailController),
              buildTextField("Phone Number", phoneController),
              buildTextField("Address", addressController),
              buildTextField("Gender", genderController),
              buildTextField("National ID", nationalIdController),
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
                onPressed: updateForm,
                child: const Text("Update"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
